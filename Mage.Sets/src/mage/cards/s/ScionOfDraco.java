package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScionOfDraco extends CardImpl {

    public ScionOfDraco(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{12}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Domain — This spell costs {2} less to cast for each basic land type among lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionForEachSourceEffect(2, DomainValue.REGULAR)
                        .setText("this spell costs {2} less to cast for each basic land type among lands you control")
        ).addHint(DomainHint.instance).setAbilityWord(AbilityWord.DOMAIN));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each creature you control has vigilance if it's white, hexproof if it's blue, lifelink if it's black, first strike if it's red, and trample if it's green.
        this.addAbility(new SimpleStaticAbility(new ScionOfDracoEffect()));
    }

    private ScionOfDraco(final ScionOfDraco card) {
        super(card);
    }

    @Override
    public ScionOfDraco copy() {
        return new ScionOfDraco(this);
    }
}

class ScionOfDracoEffect extends ContinuousEffectImpl {

    ScionOfDracoEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        staticText = "each creature you control has vigilance if it's white, hexproof if it's blue, " +
                "lifelink if it's black, first strike if it's red, and trample if it's green";
        this.addDependencyType(DependencyType.AddingAbility);
    }

    private ScionOfDracoEffect(final ScionOfDracoEffect effect) {
        super(effect);
    }

    @Override
    public ScionOfDracoEffect copy() {
        return new ScionOfDracoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source.getSourceId(), game
        )) {
            ObjectColor color = permanent.getColor(game);
            if (color.isWhite()) {
                permanent.addAbility(VigilanceAbility.getInstance(), source.getSourceId(), game);
            }
            if (color.isBlue()) {
                permanent.addAbility(HexproofAbility.getInstance(), source.getSourceId(), game);
            }
            if (color.isBlack()) {
                permanent.addAbility(LifelinkAbility.getInstance(), source.getSourceId(), game);
            }
            if (color.isRed()) {
                permanent.addAbility(FirstStrikeAbility.getInstance(), source.getSourceId(), game);
            }
            if (color.isGreen()) {
                permanent.addAbility(TrampleAbility.getInstance(), source.getSourceId(), game);
            }
        }
        return true;
    }
}
