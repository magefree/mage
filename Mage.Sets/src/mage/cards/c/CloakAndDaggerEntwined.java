package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author nandmp
 */
public final class CloakAndDaggerEntwined extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature the targeted opponent controls");

    static {
        filter.add(TargetController.SOURCE_TARGETS.getControllerPredicate());
    }

    public CloakAndDaggerEntwined(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Cloak and Dagger enter, choose target opponent and up to one target creature they control. They reveal their hand. You may exile a nonland card from their hand or the chosen creature until Cloak and Dagger leave the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CloakAndDaggerEntwinedEffect());
        ability.addTarget(new TargetOpponent());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private CloakAndDaggerEntwined(final CloakAndDaggerEntwined card) {
        super(card);
    }

    @Override
    public CloakAndDaggerEntwined copy() {
        return new CloakAndDaggerEntwined(this);
    }
}

class CloakAndDaggerEntwinedEffect extends OneShotEffect {

    CloakAndDaggerEntwinedEffect() {
        super(Outcome.Exile);
        staticText = "choose target opponent and up to one target creature they control. "
                + "They reveal their hand. You may exile a nonland card from their hand or "
                + "the chosen creature until {this} leaves the battlefield";
    }

    private CloakAndDaggerEntwinedEffect(final CloakAndDaggerEntwinedEffect effect) {
        super(effect);
    }

    @Override
    public CloakAndDaggerEntwinedEffect copy() {
        return new CloakAndDaggerEntwinedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getTargets().get(0).getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }

        opponent.revealCards(source, opponent.getHand(), game);

        // Nothing can be exiled if Cloak and Dagger left before this ability resolved.
        if (source.getSourcePermanentIfItStillExists(game) == null) {
            return true;
        }

        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        boolean hasHandCard = opponent.getHand().count(StaticFilters.FILTER_CARD_A_NON_LAND, game) > 0;
        if ((!hasHandCard && creature == null)
                || !controller.chooseUse(outcome, "Exile a card or creature?", source, game)) {
            return true;
        }

        boolean exileFromHand = hasHandCard && (creature == null || controller.chooseUse(
                outcome, "Choose what to exile", "", "Hand", "Creature", source, game
        ));
        ExileUntilSourceLeavesEffect effect;
        if (exileFromHand) {
            TargetCard target = new TargetCard(Zone.HAND, StaticFilters.FILTER_CARD_A_NON_LAND);
            if (!controller.choose(outcome, opponent.getHand(), target, source, game)) {
                return true;
            }
            Card card = opponent.getHand().get(target.getFirstTarget(), game);
            if (card == null) {
                return true;
            }
            effect = new ExileUntilSourceLeavesEffect(Zone.HAND);
            effect.setTargetPointer(new FixedTarget(card, game));
        } else {
            effect = new ExileUntilSourceLeavesEffect();
            effect.setTargetPointer(new FixedTarget(creature, game));
        }
        return effect.apply(game, source);
    }
}
