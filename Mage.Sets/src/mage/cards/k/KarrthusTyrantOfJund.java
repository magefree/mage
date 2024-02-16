package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class KarrthusTyrantOfJund extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DRAGON, "Dragons");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent(SubType.DRAGON, "Dragon creatures");

    public KarrthusTyrantOfJund(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Karrthus, Tyrant of Jund enters the battlefield, gain control of all Dragons, then untap all Dragons.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainControlAllEffect(Duration.Custom, filter));
        ability.addEffect(new UntapAllEffect(filter).concatBy(", then"));
        this.addAbility(ability);

        // Other Dragon creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter2, true
        )));
    }

    private KarrthusTyrantOfJund(final KarrthusTyrantOfJund card) {
        super(card);
    }

    @Override
    public KarrthusTyrantOfJund copy() {
        return new KarrthusTyrantOfJund(this);
    }
}
