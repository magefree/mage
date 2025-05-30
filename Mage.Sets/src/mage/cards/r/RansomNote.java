package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RansomNote extends CardImpl {

    public RansomNote(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.CLUE);

        // When Ransom Note enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1)));

        // {2}, Sacrifice Ransom Note: Choose one --
        // * Cloak the top card of your library.
        Ability ability = new SimpleActivatedAbility(
                new ManifestEffect(StaticValue.get(1), false, true), new GenericManaCost(2)
        );
        ability.addCost(new SacrificeSourceCost());

        // * Goad target creature.
        ability.addMode(new Mode(new GoadTargetEffect()).addTarget(new TargetCreaturePermanent()));

        // * Draw a card.
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(1)));
        this.addAbility(ability);
    }

    private RansomNote(final RansomNote card) {
        super(card);
    }

    @Override
    public RansomNote copy() {
        return new RansomNote(this);
    }
}
