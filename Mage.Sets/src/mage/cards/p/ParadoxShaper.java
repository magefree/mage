package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourcePreparedCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.constants.SubType;
import mage.target.common.TargetCardInYourGraveyard;
import mage.cards.PrepareCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ParadoxShaper extends PrepareCard {

    public ParadoxShaper(UUID ownerId, CardSetInfo setInfo) {
        super(
            ownerId, setInfo,
            new CardType[]{CardType.CREATURE}, "{1}{U/B}",
            "Omit Variables", new CardType[]{CardType.SORCERY}, "{U/B}"
        );

        this.subtype.add(SubType.OCTOPUS);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, if this creature isn't prepared, it becomes prepared.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
            new BecomePreparedSourceEffect(), false
        ).withInterveningIf(SourcePreparedCondition.UNPREPARED));

        // {2}: Put target card from your graveyard on the bottom of your library.
        Ability ability = new SimpleActivatedAbility(new PutOnLibraryTargetEffect(false), new GenericManaCost(2));
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);

        // Omit Variables
        // Sorcery {U/B}
        // Mill three cards. (Put the top three cards of your library into your graveyard.)
        this.getSpellCard().getSpellAbility().addEffect(new MillCardsControllerEffect(3));
    }

    private ParadoxShaper(final ParadoxShaper card) {
        super(card);
    }

    @Override
    public ParadoxShaper copy() {
        return new ParadoxShaper(this);
    }
}
