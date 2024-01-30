package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
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
public final class TenthDistrictHero extends CardImpl {

    public TenthDistrictHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {1}{W}, Collect evidence 2: Tenth District Hero becomes a Human Detective with base power and toughness 4/4 and gains vigilance.
        Ability ability = new SimpleActivatedAbility(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.HUMAN, SubType.DETECTIVE
        ), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new CollectEvidenceCost(2));
        ability.addEffect(new SetBasePowerToughnessSourceEffect(
                4, 4, Duration.Custom
        ).setText("with base power and toughness 4/4"));
        ability.addEffect(new GainAbilitySourceEffect(
                VigilanceAbility.getInstance(), Duration.Custom
        ).setText("and gains vigilance"));
        this.addAbility(ability);

        // {2}{W}, Collect evidence 4: If Tenth District Hero is a Detective, it becomes a legendary creature named Mileva, the Stalwart, it has base power and toughness 5/5, and it gains "Other creatures you control have indestructible."
        ability = new SimpleActivatedAbility(new TenthDistrictHeroEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new CollectEvidenceCost(4));
        this.addAbility(ability);
    }

    private TenthDistrictHero(final TenthDistrictHero card) {
        super(card);
    }

    @Override
    public TenthDistrictHero copy() {
        return new TenthDistrictHero(this);
    }
}

class TenthDistrictHeroEffect extends ContinuousEffectImpl {

    TenthDistrictHeroEffect() {
        super(Duration.Custom, Outcome.Benefit);
        staticText = "if {this} is a Detective, it becomes a legendary creature " +
                "named Mileva, the Stalwart, it has base power and toughness 5/5, " +
                "and it gains \"Other creatures you control have indestructible.\"";
    }

    private TenthDistrictHeroEffect(final TenthDistrictHeroEffect effect) {
        super(effect);
    }

    @Override
    public TenthDistrictHeroEffect copy() {
        return new TenthDistrictHeroEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.hasSubtype(SubType.DETECTIVE, game)) {
            discard();
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TextChangingEffects_3:
                permanent.setName("Mileva, the Stalwart");
                return true;
            case TypeChangingEffects_4:
                permanent.addSuperType(game, SuperType.LEGENDARY);
                permanent.addCardType(game, CardType.CREATURE);
                return true;
            case AbilityAddingRemovingEffects_6:
                permanent.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                        IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_CREATURES, true
                )), source.getSourceId(), game);
                return true;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setModifiedBaseValue(5);
                    permanent.getToughness().setModifiedBaseValue(5);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TextChangingEffects_3:
            case TypeChangingEffects_4:
            case AbilityAddingRemovingEffects_6:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
