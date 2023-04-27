
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.HippoToken;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LoneFox
 *
 */
public final class QuestingPhelddagrif extends CardImpl {

    public QuestingPhelddagrif(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");
        this.subtype.add(SubType.PHELDDAGRIF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {G}: Questing Phelddagrif gets +1/+1 until end of turn. Target opponent creates a 1/1 green Hippo creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                new ManaCostsImpl<>("{G}"));
        ability.addEffect(new CreateTokenTargetEffect(new HippoToken()));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {W}: Questing Phelddagrif gains protection from black and from red until end of turn. Target opponent gains 2 life.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(ProtectionAbility.from(ObjectColor.BLACK, ObjectColor.RED),
                Duration.EndOfTurn), new ManaCostsImpl<>("{W}"));
        ability.addEffect(new GainLifeTargetEffect(2));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {U}: Questing Phelddagrif gains flying until end of turn. Target opponent may draw a card.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FlyingAbility.getInstance(),
                Duration.EndOfTurn), new ManaCostsImpl<>("{U}"));
        ability.addEffect(new DrawCardTargetEffect(1, true));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private QuestingPhelddagrif(final QuestingPhelddagrif card) {
        super(card);
    }

    @Override
    public QuestingPhelddagrif copy() {
        return new QuestingPhelddagrif(this);
    }
}
