package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.keyword.AdaptEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EtheriumPteramander extends CardImpl {

    public EtheriumPteramander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.SALAMANDER);
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Etherium Pteramander can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());

        // {6}{B}: Adapt 4. This ability costs {1} less to activate for each other artifact you control.
        Ability ability = new SimpleActivatedAbility(
                new AdaptEffect(4)
                        .setText("Adapt 4. This ability costs {1} less to activate for each other artifact you control."),
                new ManaCostsImpl<>("{6}{B}")
        );
        ability.setCostAdjuster(EtheriumPteramanderAdjuster.instance);
        this.addAbility(ability.addHint(EtheriumPteramanderAdjuster.getHint()));
    }

    private EtheriumPteramander(final EtheriumPteramander card) {
        super(card);
    }

    @Override
    public EtheriumPteramander copy() {
        return new EtheriumPteramander(this);
    }
}

enum EtheriumPteramanderAdjuster implements CostAdjuster {
    instance;

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue artifactCount = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Other Artifacts you control", artifactCount);

    static Hint getHint() {
        return hint;
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int count = artifactCount.calculate(game, ability, null);
        CardUtil.reduceCost(ability, count);
    }
}
