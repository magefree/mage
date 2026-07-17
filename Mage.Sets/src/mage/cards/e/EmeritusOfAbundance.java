package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmeritusOfAbundance extends PrepareCard {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledLandPermanent("you control eight or more lands"), ComparisonType.MORE_THAN, 7
    );

    public EmeritusOfAbundance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}", "Regrowth", new CardType[]{CardType.SORCERY}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Whenever this creature attacks, if you control eight or more lands, this creature becomes prepared.
        this.addAbility(new AttacksTriggeredAbility(new BecomePreparedSourceEffect())
                .withInterveningIf(condition).addHint(LandsYouControlHint.instance));

        // Regrowth
        // Sorcery {1}{G}
        // Return target card from your graveyard to your hand.
        this.getSpellCard().getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard());
    }

    private EmeritusOfAbundance(final EmeritusOfAbundance card) {
        super(card);
    }

    @Override
    public EmeritusOfAbundance copy() {
        return new EmeritusOfAbundance(this);
    }
}
