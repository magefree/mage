package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RikuOfTwoReflections extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell");
    private static final FilterControlledCreaturePermanent filterPermanent = new FilterControlledCreaturePermanent("another nontoken creature");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
        filterPermanent.add(TokenPredicate.FALSE);
        filterPermanent.add(AnotherPredicate.instance);

    }

    public RikuOfTwoReflections(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an instant or sorcery spell, you may pay {U}{R}. If you do, copy that spell. You may choose new targets for the copy.
        Effect effect = new CopyTargetSpellEffect(true);
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(effect, new ManaCostsImpl<>("{U}{R}")), filter, false, true));

        // Whenever another nontoken creature enters the battlefield under your control, you may pay {G}{U}. If you do, create a token that's a copy of that creature.
        effect = new DoIfCostPaid(new CreateTokenCopyTargetEffect(true),
                new ManaCostsImpl<>("{G}{U}"), "Create a token that's a copy of that creature?");
        effect.setText("you may pay {G}{U}. If you do, create a token that's a copy of that creature");
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, effect, filterPermanent, false, SetTargetPointer.PERMANENT, null));
    }

    private RikuOfTwoReflections(final RikuOfTwoReflections card) {
        super(card);
    }

    @Override
    public RikuOfTwoReflections copy() {
        return new RikuOfTwoReflections(this);
    }
}
