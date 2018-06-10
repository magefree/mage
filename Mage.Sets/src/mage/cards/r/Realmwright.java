
package mage.cards.r;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseBasicLandTypeEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
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
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RealmwrightEffect2()));
    }

    public Realmwright(final Realmwright card) {
        super(card);
    }

    @Override
    public Realmwright copy() {
        return new Realmwright(this);
    }
}

class RealmwrightEffect2 extends ContinuousEffectImpl {

    public RealmwrightEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "Lands you control are the chosen type in addition to their other types";
    }

    public RealmwrightEffect2(final RealmwrightEffect2 effect) {
        super(effect);
    }

    @Override
    public RealmwrightEffect2 copy() {
        return new RealmwrightEffect2(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player you = game.getPlayer(source.getControllerId());
        List<Permanent> lands = game.getBattlefield().getAllActivePermanents(new FilterControlledLandPermanent(), source.getControllerId(), game);
        SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + ChooseBasicLandTypeEffect.VALUE_KEY));
        if (you != null && choice != null) {
            for (Permanent land : lands) {
                if (land != null) {
                    switch (layer) {
                        case TypeChangingEffects_4:
                            if (sublayer == SubLayer.NA && !land.hasSubtype(choice, game)) {
                                land.getSubtype(game).add(choice);
                            }
                            break;
                        case AbilityAddingRemovingEffects_6:
                            if (sublayer == SubLayer.NA) {
                                boolean addAbility = true;
                                if (choice.equals(SubType.FOREST)) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof GreenManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                                    }
                                }
                                if (choice.equals(SubType.PLAINS)) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof WhiteManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                                    }
                                }
                                if (choice.equals(SubType.MOUNTAIN)) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof RedManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                                    }
                                }
                                if (choice.equals(SubType.ISLAND)) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof BlueManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                                    }
                                }
                                if (choice.equals(SubType.SWAMP)) {
                                    for (Ability existingAbility : land.getAbilities()) {
                                        if (existingAbility instanceof BlackManaAbility) {
                                            addAbility = false;
                                            break;
                                        }
                                    }
                                    if (addAbility) {
                                        land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                                    }
                                }
                            }
                            break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
