package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author TheElk801
 */
public final class TransplantTheorist extends CardImpl {

    public TransplantTheorist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Transplant Theorist or another artifact enters the battlefield under your control, you may draw a card. If you do, discard a card.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1, true),
                StaticFilters.FILTER_PERMANENT_ARTIFACT, false, true
        ));

        // {2}: Put target card from your graveyard on the bottom of your library.
        Ability ability = new SimpleActivatedAbility(new PutOnLibraryTargetEffect(false), new GenericManaCost(2));
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);
    }

    private TransplantTheorist(final TransplantTheorist card) {
        super(card);
    }

    @Override
    public TransplantTheorist copy() {
        return new TransplantTheorist(this);
    }
}
