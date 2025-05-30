package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public final class BlackWizardToken extends TokenImpl {

    public BlackWizardToken() {
        super("Wizard Token", "0/1 black Wizard creature token with \"Whenever you cast a noncreature spell, this token deals 1 damage to each opponent.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.WIZARD);
        power = new MageInt(0);
        toughness = new MageInt(1);

        addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private BlackWizardToken(final BlackWizardToken token) {
        super(token);
    }

    public BlackWizardToken copy() {
        return new BlackWizardToken(this);
    }
}
