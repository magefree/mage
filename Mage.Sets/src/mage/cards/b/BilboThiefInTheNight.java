package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.SpellCastFromAnywhereOtherThanHand;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class BilboThiefInTheNight extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spells you cast from anywhere other than your hand");

    static {
        filter.add(SpellCastFromAnywhereOtherThanHand.instance);
    }

    public BilboThiefInTheNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Spells you cast from anywhere other than your hand cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever Bilbo attacks, you may cast an artifact, instant, or sorcery spell from your graveyard. If an instant or sorcery spell cast this way would be put into your graveyard, exile it instead.
        this.addAbility(new AttacksTriggeredAbility(new BilboThiefInTheNightEffect()));
    }

    private BilboThiefInTheNight(final BilboThiefInTheNight card) {
        super(card);
    }

    @Override
    public BilboThiefInTheNight copy() {
        return new BilboThiefInTheNight(this);
    }
}

class BilboThiefInTheNightEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("an artifact, instant, or sorcery card");

    static {
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.INSTANT.getPredicate(),
            CardType.SORCERY.getPredicate()
        ));
    }

    BilboThiefInTheNightEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast an artifact, instant, or sorcery spell from your graveyard. " +
                "If an instant or sorcery spell cast this way would be put into your graveyard, exile it instead";
    }

    private BilboThiefInTheNightEffect(final BilboThiefInTheNightEffect effect) {
        super(effect);
    }

    @Override
    public BilboThiefInTheNightEffect copy() {
        return new BilboThiefInTheNightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller.getGraveyard().getCards(filter, source.getControllerId(), source, game).isEmpty()) {
            return false;
        }
        if (!controller.chooseUse(Outcome.Benefit,
                "Cast an artifact, instant, or sorcery spell from your graveyard?", source, game)) {
            return true;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
        target.withNotTarget(true);
        if (!controller.choose(Outcome.Benefit, target, source, game)) {
            return false;
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        if (card.isInstantOrSorcery(game)) {
            ContinuousEffect exileEffect = new ThatSpellGraveyardExileReplacementEffect(true);
            exileEffect.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(exileEffect, source);
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
        boolean result = CardUtil.castSingle(controller, source, game, card, false, null);
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        return result;
    }
}
