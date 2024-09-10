package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.AddCreatureSubTypeAllMultiZoneEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreatureSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOwnedCreatureCard;
import mage.filter.predicate.card.FaceDownPredicate;

/**
 *
 * @author Grath
 */
public final class RoshanHiddenMagister extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Face-down creatures");
    public static final FilterControlledCreatureSpell filterSpells = new FilterControlledCreatureSpell("creature spells you control");
    public static final FilterOwnedCreatureCard filterCards = new FilterOwnedCreatureCard("creature cards you own");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public RoshanHiddenMagister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Other creatures you control are Assassins in addition to their other types. The same is true for creature spells you control and creature cards you own that aren't on the battlefield.
        this.addAbility(new SimpleStaticAbility(new AddCreatureSubTypeAllMultiZoneEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURES,
                filterSpells,
                filterCards,
                SubType.ASSASSIN
        )));

        // Face-down creatures you control have menace.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,new GainAbilityControlledEffect(
                new MenaceAbility(false),
                Duration.WhileOnBattlefield,
                filter
        )));

        // Whenever a permanent you control is turned face up, you draw a card and you lose 1 life.
        Ability ability = new TurnedFaceUpAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_A_PERMANENT
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1));
        this.addAbility(ability);
    }

    private RoshanHiddenMagister(final RoshanHiddenMagister card) {
        super(card);
    }

    @Override
    public RoshanHiddenMagister copy() {
        return new RoshanHiddenMagister(this);
    }
}
