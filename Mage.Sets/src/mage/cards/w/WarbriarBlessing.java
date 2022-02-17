package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarbriarBlessing extends CardImpl {

    public WarbriarBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When Warbriar Blessing enters the battlefield, enchanted creature fights up to one target creature you don't control.
        ability = new EntersBattlefieldTriggeredAbility(new WarbriarBlessingEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false));
        this.addAbility(ability);

        // Enchanted creature gets +0/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(0, 2)));
    }

    private WarbriarBlessing(final WarbriarBlessing card) {
        super(card);
    }

    @Override
    public WarbriarBlessing copy() {
        return new WarbriarBlessing(this);
    }
}

class WarbriarBlessingEffect extends OneShotEffect {

    WarbriarBlessingEffect() {
        super(Outcome.Benefit);
        staticText = "enchanted creature fights up to one target creature you don't control. " +
                "<i>(Each deals damage equal to its power to the other.)</i>";
    }

    private WarbriarBlessingEffect(final WarbriarBlessingEffect effect) {
        super(effect);
    }

    @Override
    public WarbriarBlessingEffect copy() {
        return new WarbriarBlessingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent opponentsPermanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null || opponentsPermanent == null) {
            return false;
        }
        Permanent attach = game.getPermanent(permanent.getAttachedTo());
        if (attach == null) {
            return false;
        }
        return attach.fight(opponentsPermanent, source, game);
    }
}
