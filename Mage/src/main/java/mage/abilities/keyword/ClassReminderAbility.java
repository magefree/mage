package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.abilities.hint.common.ClassLevelHint;
import mage.abilities.icon.CardIcon;
import mage.abilities.icon.CardIconImpl;
import mage.abilities.icon.CardIconType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public class ClassReminderAbility extends StaticAbility {

    public ClassReminderAbility() {
        super(Zone.ALL, null);
        this.addHint(ClassLevelHint.instance);
    }

    private ClassReminderAbility(final ClassReminderAbility ability) {
        super(ability);
    }

    @Override
    public ClassReminderAbility copy() {
        return new ClassReminderAbility(this);
    }

    @Override
    public String getRule() {
        return "<i>(Gain the next level as a sorcery to add its ability.)</i>";
    }

    @Override
    public List<CardIcon> getIcons(Game game) {
        if (game == null) {
            return this.icons;
        }

        // dynamic GUI icon with current level
        List<CardIcon> res = new ArrayList<>();
        Permanent permanent = this.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return res;
        }

        CardIcon levelIcon = new CardIconImpl(
                CardIconType.ABILITY_CLASS_LEVEL,
                "Current class level: " + permanent.getClassLevel(),
                String.valueOf(permanent.getClassLevel())
        );
        res.add(levelIcon);

        return res;
    }
}
