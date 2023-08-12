
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldCastTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author alexander-novo
 */
public final class PrestonTheVanisher extends CardImpl {

    private static final FilterControlledCreaturePermanent triggerFilter = new FilterControlledCreaturePermanent(
            "another nontoken creature");
    private static final FilterControlledPermanent activeCostFilter = new FilterControlledCreaturePermanent(
            SubType.ILLUSION, "Illusions");

    static {
        triggerFilter.add(TokenPredicate.FALSE);
        triggerFilter.add(AnotherPredicate.instance);
    }

    public PrestonTheVanisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{3}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever another nontoken creature enters the battlefield under your control,
        // if it wasn’t cast, create a token that’s a copy of that creature, except it’s
        // a 0/1 white Illusion.
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, null, false, 1, false, false,
                null, 0, 1, false);
        effect.setOnlyColor(ObjectColor.WHITE);
        effect.setOnlySubType(SubType.ILLUSION);
        effect.setText("create a token that's a copy of that creature, except it's a 0/1 white Illusion");
        this.addAbility(
                new EntersBattlefieldCastTriggeredAbility(Zone.BATTLEFIELD, effect, triggerFilter, false, false,
                        SetTargetPointer.PERMANENT, null,
                        true));

        // {1}{W}, Sacrifice five Illusions: Exile target nonland permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(),
                new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(5, activeCostFilter)));
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private PrestonTheVanisher(final PrestonTheVanisher card) {
        super(card);
    }

    @Override
    public PrestonTheVanisher copy() {
        return new PrestonTheVanisher(this);
    }
}
