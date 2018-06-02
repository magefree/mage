
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class QuicksilverElemental extends CardImpl {

    public QuicksilverElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {U}: Quicksilver Elemental gains all activated abilities of target creature until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new QuicksilverElementalEffect(), new ManaCostsImpl("{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // You may spend blue mana as though it were mana of any color to pay the activation costs of Quicksilver Elemental's abilities.
        QuickSilverElementalBlueManaEffect effect2 = new QuickSilverElementalBlueManaEffect();
        effect2.setTargetPointer(new FixedTarget(this.getId()));
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, effect2);
        this.addAbility(ability2);
    }

    public QuicksilverElemental(final QuicksilverElemental card) {
        super(card);
    }

    @Override
    public QuicksilverElemental copy() {
        return new QuicksilverElemental(this);
    }
}

class QuicksilverElementalEffect extends OneShotEffect {

    QuicksilverElementalEffect() {
        super(Outcome.Benefit);
        staticText = "{this} gains all activated abilities of target creature until end of turn";
    }

    QuicksilverElementalEffect(final QuicksilverElementalEffect effect) {
        super(effect);
    }

    @Override
    public QuicksilverElementalEffect copy() {
        return new QuicksilverElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Permanent creature = game.getPermanent(source.getTargets().getFirstTarget());

        if (permanent != null && creature != null) {
            for (ActivatedAbility ability : creature.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD)) {
                Ability newAbility = ability.copy();
                newAbility.newOriginalId();
                game.addEffect(new GainAbilitySourceEffect(newAbility, Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }
}

//class QuicksilverElementalEffect extends ContinuousEffectImpl {
//
//    public QuicksilverElementalEffect() {
//        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
//        staticText = "{this} gains all activated abilities of target creature until end of turn";
//    }
//
//    public QuicksilverElementalEffect(final QuicksilverElementalEffect effect) {
//        super(effect);
//    }
//
//    @Override
//    public QuicksilverElementalEffect copy() {
//        return new QuicksilverElementalEffect(this);
//    }
//
//    @Override
//    public boolean apply(Game game, Ability source) {
//        Permanent permanent = game.getPermanent(source.getSourceId());
//        Permanent creature = game.getPermanent(source.getTargets().getFirstTarget());
//
//        if (permanent != null && creature != null) {
//            for (ActivatedAbility ability : creature.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD)) {
//                permanent.addAbility(ability, source.getSourceId(), game);
//            }
//        }
//        return false;
//    }
//}

class QuickSilverElementalBlueManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public QuickSilverElementalBlueManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "You may spend blue mana as though it were mana of any color to pay the activation costs of {this}'s abilities";
    }

    public QuickSilverElementalBlueManaEffect(final QuickSilverElementalBlueManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public QuickSilverElementalBlueManaEffect copy() {
        return new QuickSilverElementalBlueManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(getTargetPointer().getFirst(game, source))) {
            if (affectedControllerId.equals(source.getControllerId())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        if (mana.getBlue() > 0) {
            return ManaType.BLUE;
        }
        return null;
    }
}
