package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DaybreakCoronet extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature with another Aura attached to it");

    static {
        filter.add(AuraAttachedPredicate.instance);
    }

    public DaybreakCoronet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature with another Aura attached to it
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +3/+3 and has first strike, vigilance, and lifelink.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(
                3, 3, Duration.WhileOnBattlefield
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.AURA
        ).setText("and has first strike"));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.AURA
        ).setText(", vigilance"));
        ability.addEffect(new GainAbilityAttachedEffect(
                LifelinkAbility.getInstance(), AttachmentType.AURA
        ).setText(", and lifelink"));
        this.addAbility(ability);
    }

    private DaybreakCoronet(final DaybreakCoronet card) {
        super(card);
    }

    @Override
    public DaybreakCoronet copy() {
        return new DaybreakCoronet(this);
    }
}

enum AuraAttachedPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        List<UUID> attachments = new LinkedList<>();
        attachments.addAll(input.getObject().getAttachments());
        return input
                .getObject()
                .getAttachments()
                .stream()
                .filter(uuid -> !uuid.equals(input.getSourceId()))
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.hasSubtype(SubType.AURA, game));
    }

    @Override
    public String toString() {
        return "Aura attached";
    }
}
