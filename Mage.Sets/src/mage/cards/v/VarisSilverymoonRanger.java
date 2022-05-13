package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.CompletedDungeonTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VarisSilverymoonRanger extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a creature or planeswalker spell");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public VarisSilverymoonRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}"), false));

        // Whenever you cast a creature or planeswalker spell, venture into the dungeon. This ability triggers only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new VentureIntoTheDungeonEffect(), filter, false
        ).setTriggersOnce(true));

        // Whenever you complete a dungeon, create a 2/2 green Wolf creature token.
        this.addAbility(new CompletedDungeonTriggeredAbility(new CreateTokenEffect(new WolfToken())));
    }

    private VarisSilverymoonRanger(final VarisSilverymoonRanger card) {
        super(card);
    }

    @Override
    public VarisSilverymoonRanger copy() {
        return new VarisSilverymoonRanger(this);
    }
}
