package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MeldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.MeldEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VanilleCheerfulLCie extends CardImpl {

    private static final Condition condition = new MeldCondition("Fang, Fearless l'Cie");

    public VanilleCheerfulLCie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.meldsWithClazz = mage.cards.f.FangFearlessLCie.class;
        this.meldsToClazz = mage.cards.r.RagnarokDivineDeliverance.class;

        // When Vanille enters, mill two cards, then return a permanent card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2));
        ability.addEffect(new VanilleCheerfulLCieEffect());
        this.addAbility(ability);

        // At the beginning of your first main phase, if you both own and control Vanille and a creature named Fang, Fearless l'Cie, you may pay {3}{B}{G}. If you do, exile them, then meld them into Ragnarok, Divine Deliverance.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(new DoIfCostPaid(
                new MeldEffect("Fang, Fearless l'Cie", "Ragnarok, Divine Deliverance")
                        .setText("exile them, then meld them into Ragnarok, Divine Deliverance"),
                new ManaCostsImpl<>("{3}{B}{G}")
        )).withInterveningIf(condition));
    }

    private VanilleCheerfulLCie(final VanilleCheerfulLCie card) {
        super(card);
    }

    @Override
    public VanilleCheerfulLCie copy() {
        return new VanilleCheerfulLCie(this);
    }
}

class VanilleCheerfulLCieEffect extends OneShotEffect {

    VanilleCheerfulLCieEffect() {
        super(Outcome.Benefit);
        staticText = ", then return a permanent card from your graveyard to your hand";
    }

    private VanilleCheerfulLCieEffect(final VanilleCheerfulLCieEffect effect) {
        super(effect);
    }

    @Override
    public VanilleCheerfulLCieEffect copy() {
        return new VanilleCheerfulLCieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(StaticFilters.FILTER_CARD_PERMANENT, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_PERMANENT);
        target.withNotTarget(true);
        player.choose(outcome, player.getGraveyard(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }
}
