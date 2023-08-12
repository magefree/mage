package mage.cards.v;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VaultOfCatlacan extends CardImpl {

    public VaultOfCatlacan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.nightCard = true;

        // <i>(Transforms from Storm the Vault.)</i>
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("<i>(Transforms from Storm the Vault.)</i>"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {T}: Add {U} for each artifact you control.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD,
                new DynamicManaEffect(Mana.BlueMana(1), ArtifactYouControlCount.instance),
                new TapSourceCost())
                .addHint(ArtifactYouControlHint.instance));

    }

    private VaultOfCatlacan(final VaultOfCatlacan card) {
        super(card);
    }

    @Override
    public VaultOfCatlacan copy() {
        return new VaultOfCatlacan(this);
    }
}
