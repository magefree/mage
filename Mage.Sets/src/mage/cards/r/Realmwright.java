package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseBasicLandTypeEffect;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Realmwright extends CardImpl {

    public Realmwright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // As Realmwright enters the battlefield, choose a basic land type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseBasicLandTypeEffect(Outcome.Neutral)));

        // Lands you control are the chosen type in addition to their other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RealmwrightEffect()));
    }

    private Realmwright(final Realmwright card) {
        super(card);
    }

    @Override
    public Realmwright copy() {
        return new Realmwright(this);
    }
}

class RealmwrightEffect extends ContinuousEffectImpl {

    public RealmwrightEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "Lands you control are the chosen type in addition to their other types";
    }

    public RealmwrightEffect(final RealmwrightEffect effect) {
        super(effect);
    }

    @Override
    public RealmwrightEffect copy() {
        return new RealmwrightEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + ChooseBasicLandTypeEffect.VALUE_KEY));
        switch (choice) {
            case PLAINS:
                dependencyTypes.add(DependencyType.BecomePlains);
                break;
            case ISLAND:
                dependencyTypes.add(DependencyType.BecomeIsland);
                break;
            case SWAMP:
                dependencyTypes.add(DependencyType.BecomeSwamp);
                break;
            case MOUNTAIN:
                dependencyTypes.add(DependencyType.BecomeMountain);
                break;
            case FOREST:
                dependencyTypes.add(DependencyType.BecomeForest);
                break;
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + ChooseBasicLandTypeEffect.VALUE_KEY));
        if (choice == null) {
            return false;
        }
        Ability ability;
        switch (choice) {
            case PLAINS:
                ability = new WhiteManaAbility();
                break;
            case ISLAND:
                ability = new BlueManaAbility();
                break;
            case SWAMP:
                ability = new BlackManaAbility();
                break;
            case MOUNTAIN:
                ability = new RedManaAbility();
                break;
            case FOREST:
                ability = new GreenManaAbility();
                break;
            default:
                ability = null;
        }
        for (Permanent land : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                source.getControllerId(), source, game
        )) {
            if (land == null || land.hasSubtype(choice, game)) {
                continue;
            }
            land.addSubType(game, choice);
            land.addAbility(ability, source.getSourceId(), game);
        }
        return true;
    }
}
