package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.continuous.NextSpellCastHasAbilityEffect;
import mage.abilities.keyword.StormAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;

/**
 *
 * @author Grath
 */
public final class StormForceOfNature extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant or sorcery spell");

    public StormForceOfNature(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Ceaseless Tempest - Whenever Storm deals combat damage to a player, the next instant or sorcery spell you
        // cast this turn has storm. (When you cast it copy it for each spell cast before it this turn. You may choose
        // new targets for the copies.)
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new NextSpellCastHasAbilityEffect(
                new StormAbility(), filter)).withFlavorWord("Ceaseless Tempest"));

    }

    private StormForceOfNature(final StormForceOfNature card) {
        super(card);
    }

    @Override
    public StormForceOfNature copy() {
        return new StormForceOfNature(this);
    }
}
