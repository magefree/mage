package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class NitaForumConciliator extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell you don't own");
    private static final FilterCard filter2 = new FilterCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
        filter2.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public NitaForumConciliator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast a spell you don't own, put a +1/+1 counter on each creature you control.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE),
            filter, false
        ));

        // {2}, Sacrifice another creature: Exile target instant or sorcery card from an opponent's graveyard. You may cast it this turn, and mana of any type can be spent to cast that spell. If that spell would be put into a graveyard, exile it instead. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new NitaForumConciliatorEffect(),
            new GenericManaCost(2)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter2));
        this.addAbility(ability);
    }

    private NitaForumConciliator(final NitaForumConciliator card) {
        super(card);
    }

    @Override
    public NitaForumConciliator copy() {
        return new NitaForumConciliator(this);
    }
}

class NitaForumConciliatorEffect extends OneShotEffect {

    NitaForumConciliatorEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target instant or sorcery card from an opponent's graveyard. " +
                "You may cast it this turn, and mana of any type can be spent to cast that spell. "
                + ThatSpellGraveyardExileReplacementEffect.RULE_A;
    }

    private NitaForumConciliatorEffect(final NitaForumConciliatorEffect effect) {
        super(effect);
    }

    @Override
    public NitaForumConciliatorEffect copy() {
        return new NitaForumConciliatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card targetCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || targetCard == null) {
            return false;
        }
        if (controller.moveCards(targetCard, Zone.EXILED, source, game)) {
            Card card = game.getCard(targetCard.getId());
            if (card != null) {
                // you may play and spend any mana
                CardUtil.makeCardPlayable(game, source, card, true, Duration.EndOfTurn, true);
                // exile from graveyard
                ContinuousEffect effect = new ThatSpellGraveyardExileReplacementEffect(false);
                effect.setTargetPointer(new FixedTarget(card, game));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}
