package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class AntManReformedRogue extends CardImpl {

    private static final FilterSpell blueFilter = new FilterSpell("a blue spell");
    private static final FilterSpell greenFilter = new FilterSpell("a green spell");

    static {
        blueFilter.add(new ColorPredicate(ObjectColor.BLUE));
        greenFilter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public AntManReformedRogue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast a green spell, Ant-Man gets +1/+0 and gains trample until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new BoostSourceEffect(1, 0, Duration.EndOfTurn).setText("{this} gets +1/+0"),
            greenFilter, false
        );
        ability.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
            .setText("and gains trample until end of turn"));
        this.addAbility(ability);

        // Whenever you cast a blue spell, Ant-Man gets -1/-0 until end of turn and can't be blocked this turn.
        ability = new SpellCastControllerTriggeredAbility(
            new BoostSourceEffect(-1, 0, Duration.EndOfTurn).setText("{this} gets -1/+0"),
            blueFilter, false
        );
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn)
            .setText("and can't be blocked this turn"));
        this.addAbility(ability);

        // Whenever Ant-Man deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
            new DrawCardSourceControllerEffect(1)
        ));
    }

    private AntManReformedRogue(final AntManReformedRogue card) {
        super(card);
    }

    @Override
    public AntManReformedRogue copy() {
        return new AntManReformedRogue(this);
    }
}
