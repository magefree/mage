
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.KalonianTwingroveTreefolkWarriorToken;

/**
 *
 * @author LevelX2
 */
public final class KalonianTwingrove extends CardImpl {

    static final FilterControlledPermanent filterLands = new FilterControlledPermanent("Forests you control");

    static {
        filterLands.add(SubType.FOREST.getPredicate());
    }

    public KalonianTwingrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Kalonian Twingrove's power and toughness are each equal to the number of Forests you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterLands))));
        // When Kalonian Twingrove enters the battlefield, create a green Treefolk Warrior creature token with "This creature's power and toughness are each equal to the number of Forests you control."
        Effect effect = new CreateTokenEffect(new KalonianTwingroveTreefolkWarriorToken());
        effect.setText("create a green Treefolk Warrior creature token with \"This creature's power and toughness are each equal to the number of Forests you control.\"");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));
    }

    private KalonianTwingrove(final KalonianTwingrove card) {
        super(card);
    }

    @Override
    public KalonianTwingrove copy() {
        return new KalonianTwingrove(this);
    }
}
