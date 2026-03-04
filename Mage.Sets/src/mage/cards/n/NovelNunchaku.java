package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author muz
 */
public final class NovelNunchaku extends CardImpl {

    public NovelNunchaku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, attach it to target creature you control. When you do, equipped creature fights up to one target creature an opponent controls.
        Ability ability = new EntersBattlefieldAttachToTarget();
        ability.addEffect(new NovelNunchakuEffect());
        this.addAbility(ability);

        // Equipped creature gets +1/+1 and has trample.
        Ability ability2 = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability2.addEffect(new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and has trample"));
        this.addAbility(ability2);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private NovelNunchaku(final NovelNunchaku card) {
        super(card);
    }

    @Override
    public NovelNunchaku copy() {
        return new NovelNunchaku(this);
    }
}

class NovelNunchakuEffect extends OneShotEffect {

    NovelNunchakuEffect() {
        super(Outcome.Benefit);
        staticText = "When you do, equipped creature fights up to one target creature an opponent controls";
    }

    private NovelNunchakuEffect(final NovelNunchakuEffect effect) {
        super(effect);
    }

    @Override
    public NovelNunchakuEffect copy() {
        return new NovelNunchakuEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null) {
            return false;
        }
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new NovelNunchakuFightEffect().setTargetPointer(new FixedTarget(creature.getId(), game)),
                false, "equipped creature fights up to one target creature an opponent controls"
        );
        reflexive.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        game.fireReflexiveTriggeredAbility(reflexive, source);
        return true;
    }
}

class NovelNunchakuFightEffect extends OneShotEffect {

    NovelNunchakuFightEffect() {
        super(Outcome.Damage);
    }

    private NovelNunchakuFightEffect(final NovelNunchakuFightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equippedCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (equippedCreature != null
                && target != null
                && equippedCreature.isCreature(game)
                && target.isCreature(game)) {
            return equippedCreature.fight(target, source, game);
        }
        return false;
    }

    @Override
    public NovelNunchakuFightEffect copy() {
        return new NovelNunchakuFightEffect(this);
    }
}
