package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.ZeppelinToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LitaMechanicalEngineer extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactCreaturePermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public LitaMechanicalEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of your end step, untap each other artifact creature you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new UntapAllEffect(filter)
                        .setText("untap each other artifact creature you control"),
                TargetController.YOU, false
        ));

        // {3}{W}, {T}: Create a 5/5 colorless Vehicle artifact token named Zeppelin with flying and crew 3.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new ZeppelinToken()), new ManaCostsImpl<>("{3}{W}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private LitaMechanicalEngineer(final LitaMechanicalEngineer card) {
        super(card);
    }

    @Override
    public LitaMechanicalEngineer copy() {
        return new LitaMechanicalEngineer(this);
    }
}
