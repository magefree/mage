package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.keyword.AirbendTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterSpellOrPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetSpellOrPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AangSwiftSavior extends CardImpl {

    private static final FilterSpellOrPermanent filter = new FilterSpellOrPermanent("other target creature or spell");

    static {
        filter.getPermanentFilter().add(CardType.CREATURE.getPredicate());
        filter.getPermanentFilter().add(AnotherPredicate.instance);
    }

    public AangSwiftSavior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.a.AangAndLaOceansFury.class;

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Aang enters, airbend up to one other target creature or spell.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AirbendTargetEffect());
        ability.addTarget(new TargetSpellOrPermanent(0, 1, filter, false));
        this.addAbility(ability);

        // Waterbend {8}: Transform Aang.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(new TransformSourceEffect(), new WaterbendCost(8)));
    }

    private AangSwiftSavior(final AangSwiftSavior card) {
        super(card);
    }

    @Override
    public AangSwiftSavior copy() {
        return new AangSwiftSavior(this);
    }
}
