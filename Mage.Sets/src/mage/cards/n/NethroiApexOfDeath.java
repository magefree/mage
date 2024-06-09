package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MutateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NethroiApexOfDeath extends CardImpl {

    public NethroiApexOfDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Mutate {4}{G/W}{B}{B}
        this.addAbility(new MutateAbility(this, "{4}{G/W}{B}{B}"));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever this creature mutates, return any number of target creature cards with total power 10 or less from your graveyard to the battlefield.
        Ability ability = new MutatesSourceTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return any number of target creature cards with total power 10 or less from your graveyard to the battlefield"));
        ability.addTarget(new NethroiApexOfDeathTarget());
        this.addAbility(ability);
    }

    private NethroiApexOfDeath(final NethroiApexOfDeath card) {
        super(card);
    }

    @Override
    public NethroiApexOfDeath copy() {
        return new NethroiApexOfDeath(this);
    }
}

class NethroiApexOfDeathTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filter
            = new FilterCreatureCard("creature cards with total power 10 or less from your graveyard");

    NethroiApexOfDeathTarget() {
        super(0, Integer.MAX_VALUE, filter);
    }

    private NethroiApexOfDeathTarget(final NethroiApexOfDeathTarget target) {
        super(target);
    }

    @Override
    public NethroiApexOfDeathTarget copy() {
        return new NethroiApexOfDeathTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        int powerSum = this
                .getTargets()
                .stream()
                .map(game::getCard)
                .map(Card::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
        return card.getPower().getValue() + powerSum <= 10;
    }
}