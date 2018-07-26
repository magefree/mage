package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 *
 * @author EikePeace
 */
public final class Hogtie extends CardImpl {

    public Hogtie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        //
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalRestrictionEffect(new CantAttackAttachedEffect(AttachmentType.AURA), HogtieCondition.instance)));
    }

    public enum HogtieCondition implements Condition {
        instance;
        private static final FilterPermanent filter = new FilterPermanent("creature");

        static {
            filter.add(new CardTypePredicate(CardType.CREATURE));
        }


        @Override
        public boolean apply(Game game, Ability source) {
            return game.getBattlefield().contains(filter, source.getControllerId(), 2, game);
        }

        @Override
        public String toString() {
            return "you control two or more creatures";
        }
    }

    public Hogtie(final Hogtie card) {
        super(card);
    }

    @Override
    public Hogtie copy() {
        return new Hogtie(this);
    }
}

