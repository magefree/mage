package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.watchers.common.ScryOrSurveilWatcher;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ScryOrSurveilCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class SurveillancePhantasm extends CardImpl {

    public SurveillancePhantasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // As long as you've scried or surveilled this turn, this creature can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(
            new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
                ScryOrSurveilCondition.instance).setText("as long as you've scried or surveilled this turn, "
                    + "this creature can attack as though it didn't have defender")
            ).addHint(ScryOrSurveilCondition.getHint())
            .addWatcher(new ScryOrSurveilWatcher()
        ));

        // {3}{U}: Surveil 1.
        this.addAbility(new SimpleActivatedAbility(
            new SurveilEffect(1),
            new ManaCostsImpl<>("{3}{U}")
        ));
    }

    private SurveillancePhantasm(final SurveillancePhantasm card) {
        super(card);
    }

    @Override
    public SurveillancePhantasm copy() {
        return new SurveillancePhantasm(this);
    }
}
