package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MeltstridersResolve extends CardImpl {

    public MeltstridersResolve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, enchanted creature fights up to one target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MeltstridersResolveEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Enchanted creature gets +0/+2 and can't be blocked by more than one creature.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(0, 2));
        ability.addEffect(new CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType.AURA)
                .setText("and can't be blocked by more than one creature"));
        this.addAbility(ability);
    }

    private MeltstridersResolve(final MeltstridersResolve card) {
        super(card);
    }

    @Override
    public MeltstridersResolve copy() {
        return new MeltstridersResolve(this);
    }
}

class MeltstridersResolveEffect extends OneShotEffect {

    MeltstridersResolveEffect() {
        super(Outcome.Benefit);
        staticText = "enchanted creature fights up to one target creature an opponent controls";
    }

    private MeltstridersResolveEffect(final MeltstridersResolveEffect effect) {
        super(effect);
    }

    @Override
    public MeltstridersResolveEffect copy() {
        return new MeltstridersResolveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .orElse(null);
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null && creature != null && permanent.fight(creature, source, game);
    }
}
