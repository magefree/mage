package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldOrCommandZoneCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class SidarJabariOfZhalfir extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.KNIGHT, "Knights");
    private static final FilterCreatureCard filter2 = new FilterCreatureCard("Knight creature card from your graveyard");

    static {
        filter2.add(SubType.KNIGHT.getPredicate());
    }

    public SidarJabariOfZhalfir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Eminence — Whenever you attack with one or more Knights, if Sidar Jabari of Zhalfir
        // is in the command zone or on the battlefield, draw a card, then discard a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
            new AttacksWithCreaturesTriggeredAbility(
                    Zone.ALL, new DrawDiscardControllerEffect(), 1, filter
            ), SourceOnBattlefieldOrCommandZoneCondition.instance,
            "Whenever you attack with one or more Knights, if {this} is in the command" +
                    " zone or on the battlefield, draw a card, then discard a card"
        );
        ability.setAbilityWord(AbilityWord.EMINENCE);
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Sidar Jabari deals combat damage to a player, return target
        // Knight creature card from your graveyard to the battlefield.
        ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);
    }

    private SidarJabariOfZhalfir(final SidarJabariOfZhalfir card) {
        super(card);
    }

    @Override
    public SidarJabariOfZhalfir copy() {
        return new SidarJabariOfZhalfir(this);
    }
}
