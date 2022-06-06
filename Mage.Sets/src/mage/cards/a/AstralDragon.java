package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AstralDragon extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public AstralDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}{U}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Project Image — When Astral Dragon enters the battlefield, create two tokens that are copies of target noncreature permanent, except they're 3/3 Dragon creatures in addition to their other types, and they have flying.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenCopyTargetEffect(
                null, CardType.CREATURE, false, 2, false,
                false, null, 3, 3, true
        ).setText("create two tokens that are copies of target noncreature permanent, " +
                "except they're 3/3 Dragon creatures in addition to their other types, and they have flying"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.withFlavorWord("Project Image"));
    }

    private AstralDragon(final AstralDragon card) {
        super(card);
    }

    @Override
    public AstralDragon copy() {
        return new AstralDragon(this);
    }
}
