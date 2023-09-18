package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureEffect;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class TyrannicalPitlord extends CardImpl {

    public TyrannicalPitlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // As Tyrannical Pitlord enters the battlefield, choose another creature you control.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureEffect()));

        // The chosen creature gets +3/+3 and has flying.
        Ability ability = new SimpleStaticAbility(new TyrannicalPitlordBoostEffect());
        ability.addEffect(new TyrannicalPitlordGainFlyingEffect());
        this.addAbility(ability);

        // When Tyrannical Pitlord leaves the battlefield, sacrifice the chosen creature.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new TyrannicalPitlordSacrificeEffect(), false));
    }

    private TyrannicalPitlord(final TyrannicalPitlord card) {
        super(card);
    }

    @Override
    public TyrannicalPitlord copy() {
        return new TyrannicalPitlord(this);
    }
}

class TyrannicalPitlordBoostEffect extends ContinuousEffectImpl {

    public TyrannicalPitlordBoostEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "the chosen creature gets +3/+3";
    }

    private TyrannicalPitlordBoostEffect(final TyrannicalPitlordBoostEffect effect) {
        super(effect);
    }

    @Override
    public TyrannicalPitlordBoostEffect copy() {
        return new TyrannicalPitlordBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object chosenCreature = game.getState().getValue(CardUtil.getCardZoneString("chosenCreature", source.getSourceId(), game));
        if (!(chosenCreature instanceof MageObjectReference)) {
            return false;
        }
        Permanent permanent = ((MageObjectReference) chosenCreature).getPermanent(game);
        if (permanent == null) {
            return false;
        }
        permanent.addPower(3);
        permanent.addToughness(3);
        return true;
    }
}

class TyrannicalPitlordGainFlyingEffect extends ContinuousEffectImpl {

    public TyrannicalPitlordGainFlyingEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "and has flying";
    }

    private TyrannicalPitlordGainFlyingEffect(final TyrannicalPitlordGainFlyingEffect effect) {
        super(effect);
    }

    @Override
    public TyrannicalPitlordGainFlyingEffect copy() {
        return new TyrannicalPitlordGainFlyingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object chosenCreature = game.getState().getValue(CardUtil.getCardZoneString("chosenCreature", source.getSourceId(), game));
        if (!(chosenCreature instanceof MageObjectReference)) {
            return false;
        }
        Permanent permanent = ((MageObjectReference) chosenCreature).getPermanent(game);
        if (permanent == null) {
            return false;
        }
        permanent.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
        return true;
    }
}

class TyrannicalPitlordSacrificeEffect extends OneShotEffect {

    public TyrannicalPitlordSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "sacrifice the chosen creature";
    }

    private TyrannicalPitlordSacrificeEffect(final TyrannicalPitlordSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public TyrannicalPitlordSacrificeEffect copy() {
        return new TyrannicalPitlordSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object chosenCreature = game.getState().getValue(CardUtil.getCardZoneString("chosenCreature", source.getSourceId(), game, true));
        if (!(chosenCreature instanceof MageObjectReference)) {
            return false;
        }
        Permanent permanent = ((MageObjectReference) chosenCreature).getPermanent(game);
        if (permanent == null) {
            return false;
        }
        permanent.sacrifice(source, game);
        return true;
    }
}
