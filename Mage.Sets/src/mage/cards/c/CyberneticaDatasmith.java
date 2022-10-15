package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPlayer;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.permanent.token.RobotToken;
import mage.target.TargetPlayer;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CyberneticaDatasmith extends CardImpl {

    private static final FilterCard filter = new FilterCard("Robots");
    private static final FilterPlayer filter2 = new FilterPlayer();

    static {
        filter.add(SubType.ROBOT.getPredicate());
        filter2.add(new AnotherTargetPredicate(2));
    }

    public CyberneticaDatasmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Protection from Robots
        this.addAbility(new ProtectionAbility(filter));

        // Field Reprogramming -- {U}, {T}: Target player draws a card. Another target player creates a 4/4 colorless Robot artifact creature token with "This creature can't block."
        Ability ability = new SimpleActivatedAbility(new DrawCardTargetEffect(1), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new CreateTokenTargetEffect(new RobotToken())
                .setTargetPointer(new SecondTargetPointer())
                .concatBy("another"));
        ability.addTarget(new TargetPlayer()
                .setTargetTag(1)
                .withChooseHint("draws a card"));
        ability.addTarget(new TargetPlayer(filter2)
                .setTargetTag(2)
                .withChooseHint("creates a token"));
        this.addAbility(ability.withFlavorWord("Field Reprogramming"));
    }

    private CyberneticaDatasmith(final CyberneticaDatasmith card) {
        super(card);
    }

    @Override
    public CyberneticaDatasmith copy() {
        return new CyberneticaDatasmith(this);
    }
}
