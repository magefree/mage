package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CantBeTargetedAttachedEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public final class CanopyCover extends CardImpl {

    private static final FilterCreaturePermanent notFlyingorReachCreatures = new FilterCreaturePermanent("except by creatures with flying or reach");
    private static final FilterStackObject filter = new FilterStackObject("spells or abilities your opponents control");

    static {
        notFlyingorReachCreatures.add(Predicates.not(
                Predicates.or(
                        new AbilityPredicate(FlyingAbility.class),
                        new AbilityPredicate(ReachAbility.class)
                )
        ));
    }

    public CanopyCover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't be blocked except by creatures with flying or reach. (!this is a static ability of the enchantment)
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesAttachedEffect(Duration.WhileOnBattlefield, notFlyingorReachCreatures, AttachmentType.AURA)));

        // Enchanted creature can't be the target of spells or abilities your opponents control.
        this.addAbility(new SimpleStaticAbility(new CantBeTargetedAttachedEffect(filter, Duration.WhileOnBattlefield, AttachmentType.AURA, TargetController.OPPONENT)));
    }

    private CanopyCover(final CanopyCover card) {
        super(card);
    }

    @Override
    public CanopyCover copy() {
        return new CanopyCover(this);
    }
}
