package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldFromGraveyardTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TriarchPraetorian extends CardImpl {

    public TriarchPraetorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Dynastic Codes -- When Triarch Praetorian enters the battlefield from a graveyard, you draw two cards and you lose 2 life.
        Ability ability = new EntersBattlefieldFromGraveyardTriggeredAbility(
                new DrawCardSourceControllerEffect(2, "you")
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));
        this.addAbility(ability.withFlavorWord("Dynastic Codes"));

        // Unearth {4}{B}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{4}{B}")));
    }

    private TriarchPraetorian(final TriarchPraetorian card) {
        super(card);
    }

    @Override
    public TriarchPraetorian copy() {
        return new TriarchPraetorian(this);
    }
}
