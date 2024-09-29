package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.EnchantAbility;
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
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SporogenicInfection extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature other than enchanted creature");

    static {
        filter.add(SporogenicInfectionPredicate.instance);
    }

    public SporogenicInfection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Sporogenic Infection enters, target player sacrifices a creature other than enchanted creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SacrificeEffect(filter, 1, "target player"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // When enchanted creature is dealt damage, destroy it.
        this.addAbility(new DealtDamageAttachedTriggeredAbility(
                Zone.BATTLEFIELD, new DestroyTargetEffect().setText("destroy it"),
                false, SetTargetPointer.PERMANENT
        ).setTriggerPhrase("When enchanted creature is dealt damage, "));
    }

    private SporogenicInfection(final SporogenicInfection card) {
        super(card);
    }

    @Override
    public SporogenicInfection copy() {
        return new SporogenicInfection(this);
    }
}

enum SporogenicInfectionPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return !Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .equals(input.getObject().getId());
    }
}
