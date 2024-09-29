package mage.cards.w;

import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.permanent.token.MercenaryToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeRideAtDawn extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("legendary creature spells you cast");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("your commander");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(Predicates.not(new AbilityPredicate(ConvokeAbility.class))); // So there are not redundant copies being added to each card
        filter2.add(CommanderPredicate.instance);
        filter2.add(TargetController.YOU.getOwnerPredicate());
    }

    public WeRideAtDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Legendary creature spells you cast have convoke.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new ConvokeAbility(), filter)));

        // Whenever your commander attacks, create a 1/1 red Mercenary creature token with "{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery."
        this.addAbility(new AttacksAllTriggeredAbility(
                new CreateTokenEffect(new MercenaryToken()), false,
                filter2, SetTargetPointer.NONE, false
        ));
    }

    private WeRideAtDawn(final WeRideAtDawn card) {
        super(card);
    }

    @Override
    public WeRideAtDawn copy() {
        return new WeRideAtDawn(this);
    }
}
