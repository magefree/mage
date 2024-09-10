
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author noxx
 */
public final class RidersOfGavony extends CardImpl {

    public RidersOfGavony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(VigilanceAbility.getInstance());

        // As Riders of Gavony enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Protect)));

        // Human creatures you control have protection from creatures of the chosen type.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RidersOfGavonyGainAbilityControlledEffect()));
    }

    private RidersOfGavony(final RidersOfGavony card) {
        super(card);
    }

    @Override
    public RidersOfGavony copy() {
        return new RidersOfGavony(this);
    }
}

class RidersOfGavonyGainAbilityControlledEffect extends ContinuousEffectImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Human creatures you control");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    protected FilterPermanent protectionFilter;

    public RidersOfGavonyGainAbilityControlledEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Human creatures you control have protection from creatures of the chosen type";
    }

    private RidersOfGavonyGainAbilityControlledEffect(final RidersOfGavonyGainAbilityControlledEffect effect) {
        super(effect);
        protectionFilter = effect.protectionFilter;
    }

    @Override
    public RidersOfGavonyGainAbilityControlledEffect copy() {
        return new RidersOfGavonyGainAbilityControlledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (protectionFilter == null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(permanent.getId(), game);
                if (subType != null) {
                    protectionFilter = new FilterPermanent(subType.getDescription() + 's');
                    protectionFilter.add(subType.getPredicate());
                } else {
                    discard();
                }
            }
        }
        if (protectionFilter != null) {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                perm.addAbility(new ProtectionAbility(protectionFilter), source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }

}
