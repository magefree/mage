package mage.cards.s;

import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DragonToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SarkhanTheMasterless extends CardImpl {

    public SarkhanTheMasterless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SARKHAN);
        this.setStartingLoyalty(5);

        // Whenever a creature attacks you or a planeswalker you control, each Dragon you control deals 1 damage to that creature.
        this.addAbility(new AttacksAllTriggeredAbility(
                new SarkhanTheMasterlessDamageEffect(),
                false, StaticFilters.FILTER_PERMANENT_A_CREATURE,
                SetTargetPointer.PERMANENT, true
        ));

        // +1: Until end of turn, each planeswalker you control becomes a 4/4 red Dragon creature and gains flying.
        this.addAbility(new LoyaltyAbility(new SarkhanTheMasterlessBecomeDragonEffect(), 1));

        // -3: Create a 4/4 red Dragon creature token with flying.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DragonToken()), -3));
    }

    private SarkhanTheMasterless(final SarkhanTheMasterless card) {
        super(card);
    }

    @Override
    public SarkhanTheMasterless copy() {
        return new SarkhanTheMasterless(this);
    }
}

class SarkhanTheMasterlessDamageEffect extends OneShotEffect {

    SarkhanTheMasterlessDamageEffect() {
        super(Outcome.Benefit);
        staticText = "each Dragon you control deals 1 damage to that creature.";
    }

    private SarkhanTheMasterlessDamageEffect(final SarkhanTheMasterlessDamageEffect effect) {
        super(effect);
    }

    @Override
    public SarkhanTheMasterlessDamageEffect copy() {
        return new SarkhanTheMasterlessDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (creature == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent != null && permanent.hasSubtype(SubType.DRAGON, game)) {
                creature.damage(1, permanent.getId(), source, game);
            }
        }
        return true;
    }
}

class SarkhanTheMasterlessBecomeDragonEffect extends ContinuousEffectImpl {

    SarkhanTheMasterlessBecomeDragonEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        staticText = "Until end of turn, each planeswalker you control becomes a 4/4 red Dragon creature and gains flying.";
    }

    private SarkhanTheMasterlessBecomeDragonEffect(final SarkhanTheMasterlessBecomeDragonEffect effect) {
        super(effect);
    }

    @Override
    public SarkhanTheMasterlessBecomeDragonEffect copy() {
        return new SarkhanTheMasterlessBecomeDragonEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent != null && permanent.isPlaneswalker(game)) {
                affectedObjectList.add(new MageObjectReference(permanent, game));
            }
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        boolean flag = false;
        for (MageObjectReference mor : affectedObjectList) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent == null) {
                continue;
            }
            flag = true;
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        permanent.removeAllCardTypes(game);
                        permanent.addCardType(game, CardType.CREATURE);
                        permanent.removeAllSubTypes(game);
                        permanent.addSubType(game, SubType.DRAGON);
                    }
                    break;
                case ColorChangingEffects_5:
                    permanent.getColor(game).setColor(ObjectColor.RED);
                    break;
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        permanent.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                    }
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setModifiedBaseValue(4);
                        permanent.getToughness().setModifiedBaseValue(4);
                    }
            }
        }
        return flag;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7
                || layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.ColorChangingEffects_5
                || layer == Layer.TypeChangingEffects_4;
    }
}
