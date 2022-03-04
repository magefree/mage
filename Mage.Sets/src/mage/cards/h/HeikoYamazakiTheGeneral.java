package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.constants.*;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class HeikoYamazakiTheGeneral extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Samurai or Warrior you control");
    private static final FilterArtifactCard filter2 = new FilterArtifactCard("artifact card from your graveyard");

    static {
        filter.add(Predicates.or(SubType.SAMURAI.getPredicate(), SubType.WARRIOR.getPredicate()));
    }

    public HeikoYamazakiTheGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a Samurai or Warrior you control attacks alone, you may cast target artifact card from your graveyard this turn.
        Ability ability = new AttacksAloneControlledTriggeredAbility(
                new PlayFromNotOwnHandZoneTargetEffect(Zone.GRAVEYARD, TargetController.YOU, Duration.EndOfTurn, false, true)
                        .setText("you may cast target artifact card from your graveyard this turn"),
                filter, false, false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);
    }

    private HeikoYamazakiTheGeneral(final HeikoYamazakiTheGeneral card) {
        super(card);
    }

    @Override
    public HeikoYamazakiTheGeneral copy() {
        return new HeikoYamazakiTheGeneral(this);
    }
}
