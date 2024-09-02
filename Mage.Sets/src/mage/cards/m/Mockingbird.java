package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.ManaSpentToCastCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.util.functions.CopyApplier;

/**
 *
 * @author Grath
 */
public final class Mockingbird extends CardImpl {

    public Mockingbird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{U}");
        
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may have Mockingbird enter the battlefield as a copy of any creature on the battlefield with mana value less than or equal to the mana spent to cast Mockingbird, except it is a Bird in addition to its other types and has flying.
        this.addAbility(new EntersBattlefieldAbility(
                new MockingbirdEffect(), true));
    }

    private Mockingbird(final Mockingbird card) {
        super(card);
    }

    @Override
    public Mockingbird copy() {
        return new Mockingbird(this);
    }
}

class MockingbirdEffect extends OneShotEffect {

    MockingbirdEffect() {
        super(Outcome.Benefit);
        staticText = "as a copy of any creature on the battlefield with mana value less than or equal to the mana " +
                "spent to cast Mockingbird, except it is a Bird in addition to its other types and has flying.";
    }

    private MockingbirdEffect(final MockingbirdEffect effect) {
        super(effect);
    }

    @Override
    public MockingbirdEffect copy() {
        return new MockingbirdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int manaSpent = ManaSpentToCastCount.instance.calculate(game, source, this);
        FilterCreaturePermanent filter = new FilterCreaturePermanent(
                "creature on the battlefield with mana value less than or equal to the amount of mana spent " +
                        "to cast Mockingbird"
        );
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, manaSpent));
        CopyPermanentEffect copyEffect = new CopyPermanentEffect(filter, new CopyApplier() {
            @Override
            public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
                blueprint.addSubType(SubType.BIRD);
                blueprint.getAbilities().add(FlyingAbility.getInstance());
                return true;
            }
        });
        return copyEffect.apply(game, source);
    }
}