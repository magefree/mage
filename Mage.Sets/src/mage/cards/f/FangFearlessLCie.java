package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FangFearlessLCie extends CardImpl {

    public FangFearlessLCie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.meldsWithClazz = mage.cards.v.VanilleCheerfulLCie.class;
        this.meldsToClazz = mage.cards.r.RagnarokDivineDeliverance.class;

        // Whenever one or more cards leave your graveyard, you draw a card and you lose 1 life. This ability triggers only once each turn.
        Ability ability = new CardsLeaveGraveyardTriggeredAbility(
                new DrawCardSourceControllerEffect(1, true)
        ).setTriggersLimitEachTurn(1);
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

        // (Melds with Vanille, Cheerful l'Cie.)
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("<i>(Melds with Vanille, Cheerful l'Cie.)</i>")
        ));
    }

    private FangFearlessLCie(final FangFearlessLCie card) {
        super(card);
    }

    @Override
    public FangFearlessLCie copy() {
        return new FangFearlessLCie(this);
    }
}
