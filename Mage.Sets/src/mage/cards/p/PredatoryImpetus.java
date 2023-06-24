package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.GoadAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PredatoryImpetus extends CardImpl {

    public PredatoryImpetus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +3/+3, must be blocked if able, and is goaded.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(3, 3));
        ability.addEffect(new PredatoryImpetusEffect());
        ability.addEffect(new GoadAttachedEffect().concatBy(","));
        this.addAbility(ability);
    }

    private PredatoryImpetus(final PredatoryImpetus card) {
        super(card);
    }

    @Override
    public PredatoryImpetus copy() {
        return new PredatoryImpetus(this);
    }
}

class PredatoryImpetusEffect extends RequirementEffect {

    PredatoryImpetusEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = ", must be blocked if able";
    }

    private PredatoryImpetusEffect(final PredatoryImpetusEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment == null || attachment.getAttachedTo() == null) {
            return false;
        }
        Permanent attachedCreature = game.getPermanent(attachment.getAttachedTo());
        return attachedCreature != null && attachedCreature.isAttacking()
                && permanent.canBlock(attachment.getAttachedTo(), game);
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public UUID mustBlockAttackerIfElseUnblocked(Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        return attachment == null ? null : attachment.getAttachedTo();
    }

    @Override
    public int getMinNumberOfBlockers() {
        return 1;
    }

    @Override
    public PredatoryImpetusEffect copy() {
        return new PredatoryImpetusEffect(this);
    }
}
