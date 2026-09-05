package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.RulebreakerAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Grath
 */
public final class SelumaLightOfAysen extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("Angel creature card");

    static {
        filter.add(SubType.ANGEL.getPredicate());
    }

    public SelumaLightOfAysen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Rulebreaker -- A deck with this commander can have Angel cards of any color identity and any basic land cards.
        this.addAbility(RulebreakerAbility.subtypeRuleBreaker(SubType.ANGEL));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Seluma deals combat damage to a player, return target Angel creature card from your graveyard to the battlefield.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private SelumaLightOfAysen(final SelumaLightOfAysen card) {
        super(card);
    }

    @Override
    public SelumaLightOfAysen copy() {
        return new SelumaLightOfAysen(this);
    }
}
