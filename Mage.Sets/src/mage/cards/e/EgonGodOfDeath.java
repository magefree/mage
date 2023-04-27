package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class EgonGodOfDeath extends ModalDoubleFacesCard {

    public EgonGodOfDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{2}{B}",
                "Throne of Death", new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{B}"
        );

        // 1.
        // Egon, God of Death
        // Legendary Creature - God
        this.getLeftHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getLeftHalfCard().setPT(new MageInt(6), new MageInt(6));

        // Deathtouch
        this.getLeftHalfCard().addAbility(DeathtouchAbility.getInstance());

        // At the beginning of your upkeep, exile two cards from your graveyard. If you can't, sacrifice Egon and draw a card.
        DoIfCostPaid effect = new DoIfCostPaid(null, new SacrificeSourceEffect(), new ExileFromGraveCost(
                new TargetCardInYourGraveyard(2, 2)), false
        );
        effect.addOtherwiseEffect(new DrawCardSourceControllerEffect(1));
        effect.setText("exile two cards from your graveyard. If you can't, sacrifice {this} and draw a card.");
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, effect, TargetController.YOU, false, false
        ));

        // 2.
        // Throne of Death
        // Legendary Artifact
        this.getRightHalfCard().addSuperType(SuperType.LEGENDARY);

        // At the beginning of your upkeep, mill a card.
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new MillCardsControllerEffect(1), TargetController.YOU, false, false
        ));

        // {2}{B}, {T}, Exile a creature card from your graveyard: Draw a card
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_A)));
        this.getRightHalfCard().addAbility(ability);
    }

    private EgonGodOfDeath(final EgonGodOfDeath card) {
        super(card);
    }

    @Override
    public EgonGodOfDeath copy() {
        return new EgonGodOfDeath(this);
    }
}
