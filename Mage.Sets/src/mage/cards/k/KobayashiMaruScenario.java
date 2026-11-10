package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.OfficerToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class KobayashiMaruScenario extends CardImpl {

    private static final FilterCreaturePermanent filter =
        new FilterCreaturePermanent("creature an opponent controls with mana value 3 or less");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public KobayashiMaruScenario(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        // At the beginning of your upkeep, choose one that hasn't been chosen --
        // * Hail -- Return up to one target creature an opponent controls with mana value 3 or less to its owner's hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ReturnToHandTargetEffect());
        ability.getModes().setLimitUsageByOnce(false);
        ability.getModes().getMode().withFlavorWord("Hail");
        ability.addTarget(new TargetPermanent(0, 1, filter));

        // * Sensors -- Draw two cards.
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(2)).withFlavorWord("Sensors"));

        // * Transport -- Create two 1/1 red Officer creature tokens.
        ability.addMode(new Mode(new CreateTokenEffect(new OfficerToken(), 2)).withFlavorWord("Transport"));

        // * Abandon Ship -- You lose the game.
        ability.addMode(new Mode(new LoseGameSourceControllerEffect()).withFlavorWord("Abandon Ship"));
        this.addAbility(ability.addHint(ModesAlreadyUsedHint.instance));
    }

    private KobayashiMaruScenario(final KobayashiMaruScenario card) {
        super(card);
    }

    @Override
    public KobayashiMaruScenario copy() {
        return new KobayashiMaruScenario(this);
    }
}
