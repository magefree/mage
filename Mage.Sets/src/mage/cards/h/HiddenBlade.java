package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HiddenBlade extends CardImpl {

    public HiddenBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Hidden Blade enters the battlefield, attach it to target creature you control. If that creature is an Assassin, it gains deathtouch until end of turn.
        Ability ability = new EntersBattlefieldAttachToTarget();
        ability.addEffect(new HiddenBladeEffect());
        this.addAbility(ability);

        // Equipped creature gets +1/+0 and has first strike.
        ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has first strike"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private HiddenBlade(final HiddenBlade card) {
        super(card);
    }

    @Override
    public HiddenBlade copy() {
        return new HiddenBlade(this);
    }
}

class HiddenBladeEffect extends OneShotEffect {

    HiddenBladeEffect() {
        super(Outcome.Benefit);
        staticText = "if that creature is an Assassin, it gains deathtouch until end of turn";
    }

    private HiddenBladeEffect(final HiddenBladeEffect effect) {
        super(effect);
    }

    @Override
    public HiddenBladeEffect copy() {
        return new HiddenBladeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || !permanent.hasSubtype(SubType.ASSASSIN, game)) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
