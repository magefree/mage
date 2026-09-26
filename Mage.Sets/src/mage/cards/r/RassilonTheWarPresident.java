package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;

import java.util.UUID;

public final class RassilonTheWarPresident extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("each noncreature spell you cast from exile");

    static {
        filter.add(new CastFromZonePredicate(Zone.EXILED));
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public RassilonTheWarPresident(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of your upkee, you lose 2 life and exile the top card of your library. You may play that card for as long as it remains exiled.
        final Ability ability = new BeginningOfUpkeepTriggeredAbility(new LoseLifeSourceControllerEffect(2), false);
        ability.addEffect(new ExileTopXMayPlayUntilEffect(1, false, Duration.EndOfGame));

        // Each noncreature spell you cast from exile has conspire.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new ConspireAbility(ConspireAbility.ConspireTargets.MORE), filter)));
    }

    private RassilonTheWarPresident(final RassilonTheWarPresident card) {
        super(card);
    }

    @Override
    public RassilonTheWarPresident copy() {
        return new RassilonTheWarPresident(this);
    }
}
