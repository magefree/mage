package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Jason E. Wall
 */
public final class TreetopBracers extends CardImpl {

    private static final FilterCreaturePermanent onlyFlyingCreatures = new FilterCreaturePermanent("except by creatures with flying");

    static {
        onlyFlyingCreatures.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public TreetopBracers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and can't be blocked except by creatures with flying.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield));
        ability.addEffect(new CantBeBlockedByCreaturesAttachedEffect(Duration.WhileOnBattlefield, onlyFlyingCreatures, AttachmentType.AURA).setText("and can't be blocked except by creatures with flying"));
        this.addAbility(ability);
    }

    private TreetopBracers(final TreetopBracers card) {
        super(card);
    }

    @Override
    public TreetopBracers copy() {
        return new TreetopBracers(this);
    }
}
