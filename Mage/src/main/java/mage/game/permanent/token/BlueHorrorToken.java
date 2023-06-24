package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

/**
 * @author TheElk801
 */
public final class BlueHorrorToken extends TokenImpl {

    public BlueHorrorToken() {
        super("Blue Horror", "2/2 blue and red Demon Horror creature token named Blue Horror with \"Whenever you cast an instant or sorcery spell, this creature deals 1 damage to any target.\"");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setRed(true);
        subtype.add(SubType.DEMON);
        subtype.add(SubType.HORROR);
        power = new MageInt(2);
        toughness = new MageInt(2);
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(1, "this creature"),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public BlueHorrorToken(final BlueHorrorToken token) {
        super(token);
    }

    public BlueHorrorToken copy() {
        return new BlueHorrorToken(this);
    }
}
