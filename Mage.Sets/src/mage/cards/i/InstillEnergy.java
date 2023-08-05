package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapAttachedEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class InstillEnergy extends CardImpl {

    public InstillEnergy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can attack as though it had haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanAttackAsThoughItHadHasteEnchantedEffect(Duration.WhileOnBattlefield)));

        // {0}: Untap enchanted creature. Activate this ability only during your turn and only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD,
                new UntapAttachedEffect(),
                new GenericManaCost(0), 1, MyTurnCondition.instance)
                .addHint(MyTurnHint.instance));
    }

    private InstillEnergy(final InstillEnergy card) {
        super(card);
    }

    @Override
    public InstillEnergy copy() {
        return new InstillEnergy(this);
    }
}

class CanAttackAsThoughItHadHasteEnchantedEffect extends AsThoughEffectImpl {

    public CanAttackAsThoughItHadHasteEnchantedEffect(Duration duration) {
        super(AsThoughEffectType.ATTACK_AS_HASTE, duration, Outcome.Benefit);
        staticText = "Enchanted creature can attack as though it had haste";
    }

    public CanAttackAsThoughItHadHasteEnchantedEffect(final CanAttackAsThoughItHadHasteEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CanAttackAsThoughItHadHasteEnchantedEffect copy() {
        return new CanAttackAsThoughItHadHasteEnchantedEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        return enchantment != null && enchantment.isAttachedTo(objectId);
    }
}
