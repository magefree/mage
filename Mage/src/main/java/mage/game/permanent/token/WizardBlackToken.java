package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

/**
 * @author balazskristof
 */
public final class WizardBlackToken extends TokenImpl {

    public WizardBlackToken() {
        super("Wizard Token", "0/1 black Wizard creature token with \"Whenever you cast a noncreature spell, this token deals 1 damage to each opponent\"");
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.WIZARD);

        // Whenever you cast a noncreature spell, this token deals 1 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT, "this token"),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private WizardBlackToken(final WizardBlackToken token) {
        super(token);
    }

    public WizardBlackToken copy() {
        return new WizardBlackToken(this);
    }
}
