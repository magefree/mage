package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Loki
 */
public final class GlissaTheTraitor extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact card from your graveyard");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public GlissaTheTraitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELF);


        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        // First strike, 
        this.addAbility(FirstStrikeAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a creature an opponent controls dies, you may return target artifact card from your graveyard to your hand.
        Ability ability = new DiesCreatureTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), true,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public GlissaTheTraitor(final GlissaTheTraitor card) {
        super(card);
    }

    @Override
    public GlissaTheTraitor copy() {
        return new GlissaTheTraitor(this);
    }
}
